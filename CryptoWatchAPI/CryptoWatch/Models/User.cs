using System;
using System.Collections.Generic;

namespace CryptoWatch.Models
{
    public partial class User
    {
        public int? IdUsers { get; set; }
        public string LoginUsers { get; set; } = null!;
        public string PasswordUsers { get; set; } = null!;
        public string NicknameUsers { get; set; } = null!;
        public string EmailUsers { get; set; } = null!;
}
}
