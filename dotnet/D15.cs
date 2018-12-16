using System;
using System.Collections.Generic;
using System.Linq;

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
            currentField[y] = new char[line.Length];
            for (var x = 0; x < line.Length; x++)
            {
                var c = line[x];
                currentField[y][x] = c;

                if (c == 'E' || c == 'G')
                    units.Add(new Unit(new Location(x, y), c));
            }
        }

        this.Units = units;
    }

    char GetChar(Location l) => this.currentField[l.y][l.x];

    bool IsWall(Location l) => GetChar(l) == '#';

    internal (Location target, Location direction) ChooseMove(Unit unit)
    {
        var inRange = Units
            .Where(u => u.Race != unit.Race)
            .SelectMany(e => Adjacent(e.Location))
            .Where(p => !IsWall(p)).ToList();

        var distances = Bfs(unit.Location, domain: ".");
        var reachable = inRange.Where(r => distances.ContainsKey(r)).ToList();
        var minDistance = reachable.Select(r => distances[r]).Min();
        var nearest = reachable.Where(r => distances[r] == minDistance).ToList();
        var chosen = nearest.OrderBy(p => p, ReadingOrderComparer.Default);

        return (target: inRange[0], direction: new Location(-10, -20));
    }

    public class ReadingOrderComparer : IComparer<Location>
    {
        public static ReadingOrderComparer Default;

        private ReadingOrderComparer() { }

        public int Compare(Location x, Location y) =>
            x.y == y.y ? x.x.CompareTo(y.x) : x.y.CompareTo(y.y);
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
        var distances = new Dictionary<Location, int>();
        distances[start] = 0;

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

    public static D15 Parse(IEnumerable<string> lines)
    {
        return new D15(lines.ToList());
    }

    public struct Location
    {
        public readonly int x;
        public readonly int y;

        public Location(int x, int y)
        {
            this.x = x;
            this.y = y;
        }

        public Location Offset(int dx, int dy) => new Location(x + dx, y + dy);
    }

    public class Unit
    {
        public Location Location;
        public int Health;
        public readonly char Race;

        public Unit(Location location, char race)
        {
            this.Location = location;
            this.Race = race;
        }
    }
}