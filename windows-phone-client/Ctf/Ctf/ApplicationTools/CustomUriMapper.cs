using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Navigation;

namespace Ctf.ApplicationTools
{
    public class CustomUriMapper : UriMapperBase
    {
        public override Uri MapUri(Uri uri)
        {
            if (uri.OriginalString == "/Pages/LaunchPage.xaml")
            {
                if (ApplicationSettings.Instance.HasLoginInfo())
                {
                    uri = new Uri("/Pages/LoggedIn.xaml", UriKind.Relative);
                }
                else
                {
                    uri = new Uri("/Pages/MainPage.xaml", UriKind.Relative);
                }
            }
            return uri;
        }
    } 
}
