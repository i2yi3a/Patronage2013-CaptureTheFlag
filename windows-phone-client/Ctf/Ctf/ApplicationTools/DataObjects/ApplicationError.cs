using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Ctf.Communication.DataObjects;

namespace Ctf.ApplicationTools.DataObjects
{
    public class ApplicationError : ServerJsonResponse
    {
        public const int APPLICATION_ERROR = -2;
        public const int SUCCESS = -1;

        public ApplicationError(string errorDescription, int errorCode = APPLICATION_ERROR)
        {
            error_code = errorCode;
            error_description = errorDescription;
        }
    }
}
