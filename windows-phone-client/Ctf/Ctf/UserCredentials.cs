using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ctf
{
    class UserCredentials
    {
        public const int MINIMAL_USERNAME_LENGTH = 5;
        public const int MINIMAL_PASSWORD_LENGTH = 5;
        private string username;
        private string password;

        public UserCredentials(string username, string password)
        {
            if (!isUsernameProperLength(username))
                throw new Exception("Username lenght should be at least " + MINIMAL_USERNAME_LENGTH + " characters long.");
            if (!isPasswordProperLength(password))
                throw new Exception("Password lenght should be at least " + MINIMAL_PASSWORD_LENGTH + " characters long.");

            this.username = username;
            this.password = password;
        }

        public static bool isUsernameProperLength(string username)
        {
            return (username.Length >= MINIMAL_USERNAME_LENGTH);
        }

        public static bool isPasswordProperLength(string password)
        {
            return (password.Length >= MINIMAL_PASSWORD_LENGTH);
        }

        public bool hasMatchingPassword(string password)
        {
            return this.password.Equals(password);
        }

        public string getUsername()
        {
            return this.username;
        }

        public string getPassword()
        {
            return this.password;
        }
    }
}
