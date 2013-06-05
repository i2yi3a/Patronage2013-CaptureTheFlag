using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ctf.ApplicationTools
{
    public class NetworkService
    {
        public static bool IsNetworkEnabled()
        {
            return (Microsoft.Phone.Net.NetworkInformation.NetworkInterface.NetworkInterfaceType !=
                Microsoft.Phone.Net.NetworkInformation.NetworkInterfaceType.None);
        }
    }
}
