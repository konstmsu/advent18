using System.Linq;

namespace Advent2018
{
    public static class D2
    {
        public static string FindClosestLines(string[] lines)
        {
            var maxOffset = lines[0].Length;

            string FindCommon(string[] ll, int offset, int? skipped)
            {
                if (offset == maxOffset)
                    return ll[0].Remove(skipped.Value, 1);

                var result = ll.GroupBy(l => l[offset])
                    .Where(g => g.Count() > 1)
                    .Select(g => FindCommon(g.ToArray(), offset + 1, skipped))
                    .FirstOrDefault(c => c != null);

                if (result != null)
                    return result;
                
                if (skipped != null)
                    return null;

                return FindCommon(ll, offset + 1, offset);
            }

            return FindCommon(lines, 0, null);
        }
    }   
}