using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;

namespace Ctf
{
    public class UserCredentials
    {
        public static int MINIMAL_USERNAME_LENGTH = 5;
        public static int MINIMAL_PASSWORD_LENGTH = 5;
        private string username;
        private string password;

        public static event EventHandler<EventArgs> MessengerSent;

        protected virtual void OnMessengerSent(EventArgs e)
        {
            var MessengerSentThreadPrivate = MessengerSent;
            if (MessengerSentThreadPrivate != null)
                MessengerSentThreadPrivate(this, e);
        }

        public UserCredentials(string username, string password)
        {
            if (!IsUsernameProperLength(username))
            {
                this.username = String.Empty;
                OnMessengerSent(new MessengerSentEventArgs("Username lenght should be at least " + MINIMAL_USERNAME_LENGTH + " characters long."));
            }
            else
            {
                this.username = username;
            }
            if (!IsPasswordProperLength(password))
            {
                this.password = String.Empty;
                OnMessengerSent(new MessengerSentEventArgs("Password lenght should be at least " + MINIMAL_PASSWORD_LENGTH + " characters long."));
            }
            else
            {
                this.password = password;
            }
        }

        public static bool IsUsernameProperLength(string username)
        {
            if(username == null)
                return false;
            return (username.Length >= MINIMAL_USERNAME_LENGTH);
        }

        public static bool IsPasswordProperLength(string password)
        {
            if(password == null)
                return false;
            return (password.Length >= MINIMAL_PASSWORD_LENGTH);
        }

        public bool HasMatchingPassword(string password)
        {
            if (password == null)
                return false;
            return this.password.Equals(password);
        }

        public string GetUsername()
        {
            return this.username;
        }

        public string GetPassword()
        {
            return this.password;
        }
    }
}
