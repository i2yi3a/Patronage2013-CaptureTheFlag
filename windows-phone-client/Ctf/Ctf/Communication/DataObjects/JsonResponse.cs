using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ctf.Communication.DataObjects
{
    public class JsonResponse
    {
        public string error { get; set; }
        public string error_description { get; set; }

        /// <summary>
        /// Determines whether this instance has error.
        /// </summary>
        /// <returns>
        ///   <c>true</c> if this instance has error; otherwise, <c>false</c>.
        /// </returns>
        public bool HasError()
        {
            return !(String.IsNullOrEmpty(error_description));
        }
    }
}
