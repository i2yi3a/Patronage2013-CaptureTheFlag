using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ctf.ApplicationTools.DataObjects
{
    public class ApplicationEventArgs : EventArgs
    {
        public ApplicationError applicationError;

        public ApplicationEventArgs(string message, int errorCode = ApplicationError.APPLICATION_ERROR)
        {
            applicationError = new ApplicationError(message, errorCode);
        }
    }
}
