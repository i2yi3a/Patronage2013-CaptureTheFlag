using Ctf.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ctf.Communication.DataObjects
{
    public class TeamBase : BindableBase
    {
        public string name { get; set; }
        public List<double> latLng { get; set; }

         public TeamBase()
        { }

         public TeamBase(string name, List<double> latLng)
         {
             this.name = name;
             this.latLng = latLng;
         }
    }
}
