using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ctf.Communication.DataObjects
{
    public class DeleteResponse : ServerResponse
    {
        public string description { get { return this.description; } set { this.description = value; this.error_description = value; } }
        public int players_count { get; set; }
    }
}
