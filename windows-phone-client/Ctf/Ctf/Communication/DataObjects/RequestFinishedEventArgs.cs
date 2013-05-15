using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ctf.Communication.DataObjects
{
    public class RequestFinishedEventArgs : EventArgs
    {
        public JsonResponse Response { get; set; }

        public RequestFinishedEventArgs(JsonResponse response)
        {
            Response = response;
        }
    }
}
