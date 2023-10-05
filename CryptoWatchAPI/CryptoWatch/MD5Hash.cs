using System.Security.Cryptography;
using System.Text;

namespace PetPortal
{
    public class MD5Hash
    {
        public string GetHash(string input)
        {
            var md5 = MD5.Create();
            var hash = md5.ComputeHash(Encoding.UTF8.GetBytes(input));

            return Convert.ToBase64String(hash);
        }

        public string GenerateValue(int length)
        {
            const string chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
            // Список символов, которые будут использоваться при генерации значения
            var rnd = new Random();
            var result = new StringBuilder(length);
            for (int i = 0; i < length; i++)
            {
                result.Append(chars[rnd.Next(chars.Length)]);
            }
            return result.ToString();
        }
    }
}
