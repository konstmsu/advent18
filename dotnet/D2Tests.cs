using System;
using Xunit;
using FluentAssertions;
using System.IO;

namespace Advent2018
{
    public class D2Tests
    {
        [Fact]
        public void Input2()
        {
            var lines = File.ReadAllLines("../../../../src/main/resources/d02.txt");
            D2.FindClosestLines(lines).Should().Be("mxhwoglxgeauywfkztndcvjqr");
        }
    }
}
