using System;
using System.Collections.Generic;

namespace CryptoWatch.Models
{
    public partial class TrackingList
    {
        public int? IdTrackingList { get; set; }
        public string NameCrypto { get; set; } = null!;
        public int? UsersId { get; set; }
    }
}
