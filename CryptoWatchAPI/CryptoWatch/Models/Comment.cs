using System;
using System.Collections.Generic;

namespace CryptoWatch.Models
{
    public partial class Comment
    {
        public int? IdComment { get; set; }
        public string Com { get; set; } = null!;
        public int? UsersId { get; set; }
    }
}
