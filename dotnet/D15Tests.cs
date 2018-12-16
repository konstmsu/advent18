using System.Linq;
using FluentAssertions;
using Xunit;
using static D15;

public class D15Tests
{
    [Fact]
    public void ShouldExtractUnits()
    {
        var game = D15.Parse(new[] {
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
        var game = D15.Parse(new[] {
"#######",
"#E..G.#",
"#...#.#",
"#.G.#G#",
"#######"});

        var move = game.ChooseMove(game.Units[0]);
        move.target.Should().Be((3, 1));
        move.direction.Should().Be((1, 0));
    }

    [Fact]
    public void ShouldFollowReadingOrder()
    {
        var points = new [] 
        {
            new Location(1, 1),
            new Location(0, 1),
            new Location(1, 0),
            new Location(0, 0),
        };
        points.OrderBy(l => l, ReadingOrderComparer.Default).Should().Equal(
            new Location(0, 0),
            new Location(0, 1),
            new Location(1, 0),
            new Location(1, 1)
        );
    }
}