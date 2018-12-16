using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Text;

public class D15
{
    public readonly List<Unit> Units;
    char[][] currentField;

    public D15(IReadOnlyList<string> lines)
    {
        var units = new List<Unit>();
        currentField = new char[lines.Count][];
        for (var y = 0; y < lines.Count; y++)
        {
            var line = lines[y];

            if (line.Length != lines[0].Length)
                throw new ArgumentOutOfRangeException(nameof(lines), "Field is not uniform");

            currentField[y] = new char[line.Length];
            for (var x = 0; x < line.Length; x++)
            {
                var c = line[x];
                currentField[y][x] = c;

                if (c == 'E' || c == 'G')
                    units.Add(new Unit((x, y), c));
            }
        }

        Units = units;
    }

    char GetChar(Location l) => currentField[l.Y][l.X];
    char SetChar(Location l, char value) => currentField[l.Y][l.X] = value;

    bool IsWall(Location l) => GetChar(l) == '#';

    public Location? ChooseNextMove(Unit unit)
    {
        var inRange = Units
            .Where(u => u.Race != unit.Race)
            .SelectMany(e => Adjacent(e.Location))
            .Where(p => !IsWall(p));

        if (inRange.Contains(unit.Location))
            throw new InvalidOperationException("Not supposed to move as have enemy in range");

        var distanceFromUnit = Bfs(unit.Location, domain: ".");
        var reachable = inRange.Where(r => distanceFromUnit.ContainsKey(r)).ToList();

        if (!reachable.Any())
            return null;

        var minDistance = reachable.Select(r => distanceFromUnit[r]).Min();
        var nearest = reachable.Where(r => distanceFromUnit[r] == minDistance);
        var chosen = nearest.OrderBy(p => p).First();

        var distanceFromChosen = Bfs(chosen, domain: ".");
        var moves = Adjacent(unit.Location).Where(a => distanceFromChosen.ContainsKey(a)).OrderBy(a => distanceFromChosen[a]).ThenBy(l => l).ToList();
        var moveTo = moves.First();

        return moveTo;
    }

    public string Render(bool includeHealth)
    {
        var result = new List<StringBuilder>();
        for (var y = 0; y < currentField.Length; y++)
        {
            var line = new StringBuilder(new string(currentField[y]));
            if (includeHealth)
            {
                var units = Units.Where(u => u.Location.Y == y).OrderBy(u => u.Location.X).ToList();
                if (units.Any())
                {
                    var healths = string.Join(", ", units.Select(u => $"{u.Race}({u.Health})"));
                    line.Append("   ");
                    line.Append(healths);
                }
            }
            result.Add(line);
        }
        return string.Join(Environment.NewLine, result);
    }

    public bool IsCombatOver { get; private set; }
    public int CurrentRound { get; private set; }

    internal void PlayRound()
    {
        if (IsCombatOver)
            throw new InvalidOperationException();

        CurrentRound++;
        foreach (var unit in Units.OrderBy(u => u.Location).ToList())
        {
            if (unit.IsDead)
                continue;

            if (Units.Select(u => u.Race).Distinct().Count() < 2)
            {
                IsCombatOver = true;
                break;
            }

            if (!TryAttack(unit))
            {
                var nextStep = ChooseNextMove(unit);

                if (nextStep.HasValue)
                    Move(unit, nextStep.Value);

                TryAttack(unit);
            }
        }
    }

    private bool TryAttack(Unit unit)
    {
        var adjacent = Adjacent(unit.Location);
        var enemies = Units.Where(u => u.Race != unit.Race && adjacent.Contains(u.Location)).OrderBy(e => e.Health).ThenBy(e => e.Location).ToList();
        if (!enemies.Any())
            return false;

        var enemy = enemies.First();
        enemy.Health -= unit.AttackPower;

        if (enemy.Health <= 0)
            Die(enemy);

        return true;
    }

    public static D15 Solve1(string input)
    {
        var game = Parse(input.Trim().Split(Environment.NewLine));

        while (!game.IsCombatOver)
            game.PlayRound();

        return game;
    }

    public static D15 Solve2(string input)
    {
        for (var elfAttack = 4; elfAttack < 200; elfAttack++)
        {
            var game = Parse(input.Trim().Split(Environment.NewLine));
            var elves = game.Units.Where(u => u.Race == 'E');

            foreach (var elf in elves)
                elf.AttackPower = elfAttack;

            var elfCount = elves.Count();

            while (!game.IsCombatOver)
                game.PlayRound();

            if (elfCount == elves.Count())
                return game;
        }

        throw new Exception();
    }

    public int Solution => (CurrentRound - 1) * Units.Sum(u => u.Health);

    void Die(Unit unit)
    {
        if (GetChar(unit.Location) != unit.Race)
            throw new Exception();

        SetChar(unit.Location, '.');
        unit.IsDead = true;
        Units.Remove(unit);
    }

    private void Move(Unit unit, Location location)
    {
        var canMoveTo = Adjacent(unit.Location);
        if (!canMoveTo.Contains(location))
            throw new ArgumentOutOfRangeException(nameof(location), $"Can't move from {unit.Location} to {location}. Allowed to move only to {string.Join(", ", canMoveTo)}");

        if (GetChar(unit.Location) != unit.Race)
            throw new InvalidOperationException();

        if (GetChar(location) != '.')
            throw new InvalidOperationException();

        SetChar(unit.Location, '.');
        unit.Location = location;
        SetChar(unit.Location, unit.Race);
    }

    private static Location[] Adjacent(Location l) => new[]
    {
        l.Offset(1, 0),
        l.Offset(-1, 0),
        l.Offset(0, -1),
        l.Offset(0, 1),
    };

    Dictionary<Location, int> Bfs(Location start, string domain)
    {
        var distances = new Dictionary<Location, int>
        {
            [start] = 0
        };

        var toVisit = new Queue<Location>();
        toVisit.Enqueue(start);

        while (toVisit.TryDequeue(out var l))
            foreach (var child in Adjacent(l))
                if (domain.Contains(GetChar(child)) && !distances.ContainsKey(child))
                {
                    distances[child] = distances[l] + 1;
                    toVisit.Enqueue(child);
                }

        return distances;
    }

    string RenderDistances(Dictionary<Location, int> distances)
    {
        var lines = currentField.Select(l => new StringBuilder(new string(l))).ToList();

        foreach (var l in distances.Keys)
            lines[l.Y][l.X] = (char)('0' + distances[l] % 10);

        return string.Join(Environment.NewLine, lines);
    }

    public static D15 Parse(IEnumerable<string> lines)
    {
        return new D15(lines.ToList());
    }

    public struct Location : IComparable<Location>
    {
        public readonly int X;
        public readonly int Y;

        public Location(int x, int y)
        {
            X = x;
            Y = y;
        }

        // Somehow OrderBy didn't work when passing an IComparer<Location>. So implementing IComparable<Location> as a workaround
        public int CompareTo(Location o) => Y == o.Y ? X.CompareTo(o.X) : Y.CompareTo(o.Y);

        public Location Offset(int dx, int dy) => (X + dx, Y + dy);

        public static implicit operator Location((int x, int y) value) => new Location(value.x, value.y);

        public static Location operator -(Location l, Location r) => (l.X - r.X, l.Y - r.Y);

        public override string ToString() => $"{X},{Y}";
    }

    public class Unit
    {
        public readonly char Race;
        public int AttackPower = 3;

        public Location Location;
        public int Health = 200;
        public bool IsDead;

        public Unit(Location location, char race)
        {
            Location = location;
            Race = race;
        }
    }
}