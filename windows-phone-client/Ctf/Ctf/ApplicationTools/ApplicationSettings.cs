using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Diagnostics;
using System.Reflection;
using System.IO.IsolatedStorage;
using Ctf.ApplicationTools.DataObjects;
using Ctf.Communication.DataObjects;

namespace Ctf.ApplicationTools
{
    public delegate void UserChangedEventHandler(object sender, ApplicationEventArgs e);

    // Reference: http://csharpindepth.com/articles/general/singleton.aspx
    /// <summary>
    /// 
    /// </summary>
    public sealed class ApplicationSettings
    {
        static readonly ApplicationSettings instance = new ApplicationSettings();
        private IsolatedStorageSettings settings;
        private const string userKeyword = "user";
        public event UserChangedEventHandler UserChanged;

        /// <summary>
        /// Raises the <see cref="E:UserChanged" /> event.
        /// </summary>
        /// <param name="e">The <see cref="EventArgs"/> instance containing the event data.</param>
        private void OnUserChanged(ApplicationEventArgs e)
        {
            var UserChangedThreadPrivate = UserChanged;
            if (UserChangedThreadPrivate != null)
                UserChangedThreadPrivate(this, e);
        }

        /// <summary>
        /// Initializes the <see cref="ApplicationSettings"/> class.
        /// </summary>
        static ApplicationSettings()
        {
        }

        /// <summary>
        /// Prevents a default instance of the <see cref="ApplicationSettings"/> class from being created.
        /// </summary>
        ApplicationSettings()
        {
            settings = IsolatedStorageSettings.ApplicationSettings;
        }

        /// <summary>
        /// Gets the instance.
        /// </summary>
        /// <value>
        /// The instance.
        /// </value>
        public static ApplicationSettings Instance
        {
            get { return instance; }
        }

        // Reference : http://www.geekchamp.com/tips/all-about-wp7-isolated-storage-store-data-in-isolatedstoragesettings
        /// <summary>
        /// Saves the logged user.
        /// </summary>
        /// <param name="user">The user.</param>
        /// <returns></returns>
        public bool SaveLoggedUser(User user)
        {
            if ((user != null) && (!user.HasNullOrEmpty()) && (!settings.Contains(userKeyword)))
            {
                Debug.WriteLine(DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), String.Format("Adding user {0} to storage.", user.username)));
                settings.Add(userKeyword, user);
                // TDOD Localize string
                OnUserChanged(new ApplicationEventArgs(String.Format("User {0} has been saved.", user.username), ApplicationError.SUCCESS));
                return true;
            }
            return false;
        }

        /// <summary>
        /// Retrives the logged user.
        /// </summary>
        /// <returns></returns>
        public User RetriveLoggedUser()
        {
            Debug.WriteLineIf(!settings.Contains(userKeyword), DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "IsolatedStorageSettings does NOT contain user."));
            Debug.WriteLineIf(!settings.Contains(userKeyword), DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "IsolatedStorageSettings DOES contain user."));
            User user = new User();
            if (settings.Contains(userKeyword))
            {
                settings.TryGetValue<User>(userKeyword, out user);
            }
            return user;
        }

        /// <summary>
        /// Removes from settings.
        /// </summary>
        /// <param name="keyword">The keyword.</param>
        /// <returns></returns>
        public bool RemoveFromSettings(string keyword)
        {
            if (settings.Contains(keyword))
            {
                string username;
                Debug.WriteLine(DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), String.Format("Removing user {0} from storage.", username = RetriveLoggedUser().username)));
                settings.Remove(keyword);
                // TDOD Localize string
                OnUserChanged(new ApplicationEventArgs(String.Format("User {0} has been removed from IsolatedStorageSettings.", username), ApplicationError.SUCCESS));
                return true;
            }
            return false;
        }

        /// <summary>
        /// Determines whether [has login info].
        /// </summary>
        /// <returns>
        ///   <c>true</c> if [has login info]; otherwise, <c>false</c>.
        /// </returns>
        public bool HasLoginInfo()
        {
            return settings.Contains(userKeyword);
        }
    }
}
