using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ctf
{
    class MessengerSentEventArgs : EventArgs
    {
        public readonly string message;

        public MessengerSentEventArgs(string message)
        {
            this.message = message;
        }
    }
}
