using System.IO;

namespace Advent2018
{
    static class FileLocator
    {
        public static string Input(string fileName) => Path.Combine("../../../../_input/", fileName);

    }
}
