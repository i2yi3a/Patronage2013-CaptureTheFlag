using Ctf.Communication.DataObjects;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ctf.Models.DataObjects
{
    public class GameDetails : GameHeader
    {
        public string description { get; set; }
        public int duration { get; set; }
        public List<string> players { get; set; }
        public int points_max { get; set; }
        public TeamBase red_team_base { get; set; }
        public TeamBase blue_team_base { get; set; }
    }
}
