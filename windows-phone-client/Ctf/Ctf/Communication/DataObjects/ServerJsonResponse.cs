using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ctf.Communication.DataObjects
{
    public class ServerJsonResponse : JsonResponse
    {
        public int error_code { get; set; }
        //Server zwraca przy success {"message":"OK","error_code":0}
        public string message { get; set; }
    }
}
