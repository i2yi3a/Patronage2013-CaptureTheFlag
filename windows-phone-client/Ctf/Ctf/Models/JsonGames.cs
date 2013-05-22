using Ctf.Communication.DataObjects;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ctf.Models
{
    public class JsonGames : ServerJsonResponse
    {
        public List<GameHeader> games { get; set; }
    }
}
