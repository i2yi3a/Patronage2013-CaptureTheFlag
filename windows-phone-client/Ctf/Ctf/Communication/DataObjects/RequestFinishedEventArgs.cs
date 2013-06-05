using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ctf.Communication.DataObjects
{
    public class RequestFinishedEventArgs : EventArgs
    {
        public ResponseBase Response { get; set; }

        public RequestFinishedEventArgs(ResponseBase response)
        {
            Response = response;
        }
    }
}
