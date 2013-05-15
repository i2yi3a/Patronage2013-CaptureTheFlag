using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ctf.ApplicationTools.DataObjects
{
    public class ApplicationEventArgs : EventArgs
    {
        public readonly string message;
        public readonly int errorCode;

        public ApplicationEventArgs(string message, int errorCode = ApplicationError.APPLICATION_ERROR)
        {
            this.message = message;
            this.errorCode = errorCode;
        }
    }
}
