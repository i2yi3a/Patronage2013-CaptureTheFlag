using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ctf.ApplicationTools.DataObjects
{
    public class LoginCredentials : UserCredentials
    {
        public string secret { get; set; }

        public LoginCredentials(string username, string password, string secret)
            : base(username, password)
        {
            this.secret = secret;
        }
    }
}
