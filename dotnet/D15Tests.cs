using System;
using System.IO;
using System.Linq;
using Advent2018;
using FluentAssertions;
using Xunit;
using static D15;

public class D15Tests
{
    [Fact]
    public void ShouldExtractUnits()
    {
        var game = Parse(new[] {
"#######",
"#E..G.#",
"#...#.#",
"#.G.#G#",
"#######"});

        game.Units.Should().HaveCount(4);
    }

    [Fact]
    public void ShouldChooseMove()
    {
        var game = Parse(new[] {
"#######",
"#E..G.#",
"#...#.#",
"#.G.#G#",
"#######"});

        var move = game.ChooseNextMove(game.Units[0]);
        move.Should().Be(new Location(2, 1));
    }

    [Fact]
    public void ShouldFollowReadingOrder()
    {
        var points = new Location[]
        {
            (1, 1),
            (0, 1),
            (1, 0),
            (0, 0),
        };
        points.ToList().OrderBy(l => l).ToList().Should().Equal(
            new Location(0, 0),
            (1, 0),
            (0, 1),
            (1, 1)
        );
    }

    [Fact]
    public void ShouldEqualityCompareLocations()
    {
        new Location(1, 2).Should().Be((1, 2));
        new Location(1, 2).Should().NotBe((2, 1));
    }

    [Fact]
    public void ShouldMoveAsInSample()
    {
        var game = Parse(new[] {
"#########",
"#G..G..G#",
"#.......#",
"#.......#",
"#G..E..G#",
"#.......#",
"#.......#",
"#G..G..G#",
"#########"});

        game.PlayRound();
        game.Render(includeHealth: false).Should().Be(@"#########
#.G...G.#
#...G...#
#...E..G#
#.G.....#
#.......#
#G..G..G#
#.......#
#########");

        game.PlayRound();
        game.Render(includeHealth: false).Should().Be(@"#########
#..G.G..#
#...G...#
#.G.E.G.#
#.......#
#G..G..G#
#.......#
#.......#
#########");

        game.PlayRound();
        game.Render(includeHealth: false).Should().Be(@"#########
#.......#
#..GGG..#
#..GEG..#
#G..G...#
#......G#
#.......#
#.......#
#########");
    }


    [Fact]
    public void ShouldPlay()
    {
        var game = Parse(@"#######
#.G...#
#...EG#
#.#.#G#
#..G#E#
#.....#
#######".Split(Environment.NewLine));

        var rounds = 0;
        void GoToRound(int target)
        {
            if (target < rounds)
                throw new ArgumentOutOfRangeException(nameof(target));

            while (rounds < target)
            {
                game.PlayRound();
                rounds++;
            }
        }

        GoToRound(1);
        game.Render(includeHealth: true).Should().Be(@"#######
#..G..#   G(200)
#...EG#   E(197), G(197)
#.#G#G#   G(200), G(197)
#...#E#   E(197)
#.....#
#######");

        GoToRound(2);
        game.Render(includeHealth: true).Should().Be(@"#######
#...G.#   G(200)
#..GEG#   G(200), E(188), G(194)
#.#.#G#   G(194)
#...#E#   E(194)
#.....#
#######");

        GoToRound(23);
        game.Render(includeHealth: true).Should().Be(@"#######
#...G.#   G(200)
#..G.G#   G(200), G(131)
#.#.#G#   G(131)
#...#E#   E(131)
#.....#
#######");

        GoToRound(24);
        game.Render(includeHealth: true).Should().Be(@"#######
#..G..#   G(200)
#...G.#   G(131)
#.#G#G#   G(200), G(128)
#...#E#   E(128)
#.....#
#######");

        GoToRound(25);
        game.Render(includeHealth: true).Should().Be(@"#######
#.G...#   G(200)
#..G..#   G(131)
#.#.#G#   G(125)
#..G#E#   G(200), E(125)
#.....#
#######");

        GoToRound(26);
        game.Render(includeHealth: true).Should().Be(@"#######
#G....#   G(200)
#.G...#   G(131)
#.#.#G#   G(122)
#...#E#   E(122)
#..G..#   G(200)
#######");

        GoToRound(27);
        game.Render(includeHealth: true).Should().Be(@"#######
#G....#   G(200)
#.G...#   G(131)
#.#.#G#   G(119)
#...#E#   E(119)
#...G.#   G(200)
#######");

        GoToRound(28);
        game.Render(includeHealth: true).Should().Be(@"#######
#G....#   G(200)
#.G...#   G(131)
#.#.#G#   G(116)
#...#E#   E(113)
#....G#   G(200)
#######");

        GoToRound(47);
        game.Render(includeHealth: true).Should().Be(@"#######
#G....#   G(200)
#.G...#   G(131)
#.#.#G#   G(59)
#...#.#
#....G#   G(200)
#######");
    }

    [Fact]
    public void Sample11()
    {
        var game = Solve1(@"#######
#G..#E#
#E#E.E#
#G.##.#
#...#E#
#...E.#
#######");
        game.Solution.Should().Be(36334);
        game.Render(true).Should().Be(@"#######
#...#E#   E(200)
#E#...#   E(197)
#.E##.#   E(185)
#E..#E#   E(200), E(200)
#.....#
#######");
    }

    [Fact]
    public void Sample12()
    {
        var game = Solve1(@"#######
#E..EG#
#.#G.E#
#E.##E#
#G..#.#
#..E#.#
#######");
        game.Solution.Should().Be(39514);
        game.Render(true).Should().Be(@"#######
#.E.E.#   E(164), E(197)
#.#E..#   E(200)
#E.##.#   E(98)
#.E.#.#   E(200)
#...#.#
#######");
    }

    [Fact]
    public void Sample13()
    {
        var game = Solve1(@"#######
#E.G#.#
#.#G..#
#G.#.G#
#G..#.#
#...E.#
#######");

        game.Solution.Should().Be(27755);
        game.Render(true).Should().Be(@"#######
#G.G#.#   G(200), G(98)
#.#G..#   G(200)
#..#..#
#...#G#   G(95)
#...G.#   G(200)
#######");
    }

    [Fact]
    public void Sample14()
    {
        var game = Solve1(@"#######
#.E...#
#.#..G#
#.###.#
#E#G#G#
#...#G#
#######");

        game.Solution.Should().Be(28944);
        game.Render(true).Should().Be(@"#######
#.....#
#.#G..#   G(200)
#.###.#
#.#.#.#
#G.G#G#   G(98), G(38), G(200)
#######");
    }

    [Fact]
    public void Sample15()
    {
        var game = Solve1(@"#########
#G......#
#.E.#...#
#..##..G#
#...##..#
#...#...#
#.G...G.#
#.....G.#
#########");

        game.Solution.Should().Be(18740);
        game.Render(true).Should().Be(@"#########
#.G.....#   G(137)
#G.G#...#   G(200), G(200)
#.G##...#   G(200)
#...##..#
#.G.#...#   G(200)
#.......#
#.......#
#########");
    }

    [Fact]
    public void Input1()
    {
        var game = Solve1(string.Join(Environment.NewLine, File.ReadLines(FileLocator.Input("d15.txt"))));
        game.Render(includeHealth: true).Should().Be(@"################################
#...############################
###..###########################
##.....#########################
#......#########################
##......########################
#.......########################
###....#########################
###....#########################
######...#######################
#######....#####################
###..#...G.....G......##########   G(200), G(140)
##......G..G..#####...##.#######   G(200), G(200)
#.......G...G#######...#..######   G(176), G(200)
#...####....#########......#####
#...##.#....#########.......####
#...##......#########.........##
#...##.G....#########.......####   G(200)
#.....G.....#########.......####   G(200)
#......GG....#######...........#   G(143), G(200)
#............G#####............#   G(59)
#.............G....G.........###   G(131), G(2)
#..............G.#####.#..######   G(107)
#..#..........G.G.####...#######   G(116), G(200)
#..#...........G..######.#######   G(200)
####.#...........###############
########..##...#################
##...##..###..##################
#.......########################
##......########################
###......#######################
################################");
        game.Solution.Should().Be(227290);
    }

    [Fact]
    public void Sample2()
    {
        var game = Solve2(@"#######
#.G...#
#...EG#
#.#.#G#
#..G#E#
#.....#
#######");

        game.Render(includeHealth: true).Should().Be(@"#######
#..E..#   E(158)
#...E.#   E(14)
#.#.#.#
#...#.#
#.....#
#######");

        game.Solution.Should().Be(4988);
    }


    [Fact]
    public void Sample22()
    {
        var game = Solve2(@"#######
#E..EG#
#.#G.E#
#E.##E#
#G..#.#
#..E#.#
#######");
        game.Solution.Should().Be(31284);
    }

    [Fact]
    public void Sample23()
    {
        var game = Solve2(@"#######
#E.G#.#
#.#G..#
#G.#.G#
#G..#.#
#...E.#
#######");

        game.Solution.Should().Be(3478);
    }

    [Fact]
    public void Sample24()
    {
        var game = Solve2(@"#######
#.E...#
#.#..G#
#.###.#
#E#G#G#
#...#G#
#######");

        game.Solution.Should().Be(6474);
    }

    [Fact]
    public void Sample25()
    {
        var game = Solve2(@"#########
#G......#
#.E.#...#
#..##..G#
#...##..#
#...#...#
#.G...G.#
#.....G.#
#########");

        game.Solution.Should().Be(1140);
    }


    [Fact]
    public void Input2()
    {
        var game = Solve2(string.Join(Environment.NewLine, File.ReadLines(FileLocator.Input("d15.txt"))));
        game.Render(includeHealth: true).Should().Be(@"################################
#...############################
###..###########################
##.....#########################
#......#########################
##......########################
#.......########################
###....#########################
###....#########################
######...#######################
#######E.E.#####################   E(200), E(125)
###..#..EE.E..........##########   E(182), E(182), E(95)
##...E...E....#####...##.#######   E(107), E(62)
#.......E....#######...#..######   E(200)
#...####....#########......#####
#...##.#....#########.......####
#...##......#########.........##
#...##......#########.......####
#.........E.#########.......####   E(182)
#............#######...........#
#.............#####............#
#............E...............###   E(200)
#................#####.#..######
#..#..............####...#######
#..#..............######.#######
####.#...........###############
########..##...#################
##...##..###..##################
#.......########################
##......########################
###......#######################
################################");
        game.Solution.Should().Be(53725);
    }

}