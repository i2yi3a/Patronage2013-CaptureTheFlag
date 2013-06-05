using Ctf.Communication.DataObjects;
using Ctf.Models.DataObjects;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ctf.Models
{
    public class GamesList : ServerResponse
    {
        public List<GameHeader> games { get; set; }
    }
}
