using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;

namespace Ctf.ApplicationTools.DataObjects
{
    public class UserCredentials
    {
        /// <summary>
        /// The minimum length of username string.
        /// </summary>
        public static int MINIMAL_USERNAME_LENGTH = 5;
        /// <summary>
        /// The minimum length of password string.
        /// </summary>
        public static int MINIMAL_PASSWORD_LENGTH = 5;
        public string username { get; set; }
        public string password { get; set; }

        /// <summary>
        /// Initializes a new instance of the <see cref="UserCredentials"/> class.
        /// </summary>
        /// <param name="username">The username.</param>
        /// <param name="password">The password.</param>
        public UserCredentials(string username, string password)
        {
            this.username = username;
            this.password = password;
        }
    }
}
