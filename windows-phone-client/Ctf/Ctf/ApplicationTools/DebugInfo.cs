using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Diagnostics;
using System.Reflection;

namespace Ctf.ApplicationTools
{
    public static class DebugInfo
    {
        public static string Format(DateTime t, Object o, MethodBase m, String msg)
        {
            return "[" + t.ToString("HH:mm:ss") + "](" + o.GetHashCode() + "){ " + m.ToString() + " } >> " + msg;
        }
        public static string Format(DateTime t, Object o, String m, String msg)
        {
            return "[" + t.ToString("HH:mm:ss") + "](" + o.GetHashCode() + "){ " + m.ToString() + " } >> " + msg;
        }
    }
}
