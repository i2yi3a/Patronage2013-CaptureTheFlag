using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Diagnostics;
using System.IO.IsolatedStorage;

namespace Ctf
{

    // Issue: https://tracker.blstreamgroup.com/jira/browse/CTFPAT-92
    // TODO incorporate events
    public sealed class ApplicationSettings
    {
        static readonly ApplicationSettings instance = new ApplicationSettings();
        IsolatedStorageSettings settings;

        // Explicit static constructor to tell C# compiler
        // not to mark type as beforefieldinit
        static ApplicationSettings()
        {
            Debug.WriteLine("static constr");
        }

        ApplicationSettings()
        {
            settings = IsolatedStorageSettings.ApplicationSettings;
            Debug.WriteLine("private constr");
        }

        public static ApplicationSettings Instance
        {
            get
            {
                return instance;
            }
        }

        // Reference : http://www.geekchamp.com/tips/all-about-wp7-isolated-storage-store-data-in-isolatedstoragesettings
        public void SaveLoginSession(LoginResponse session)
        {
            if (session != null)
            {
                if (!settings.Contains("session"))
                {
                    Debug.WriteLineIf(settings == null, "Settings is null!");
                    settings.Add("session", session);
                }
            }
        }

        public LoginResponse RetriveLoginSession()
        {
            LoginResponse session = null;
            if (settings.Contains("session"))
            {
                settings.TryGetValue<LoginResponse>("session", out session);
            }
            return session;
        }


        public void clearSettings()
        {
            settings.Remove("session");
        }

    }
}
