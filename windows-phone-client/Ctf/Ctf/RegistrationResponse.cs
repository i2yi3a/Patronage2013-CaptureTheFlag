﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ctf
{
    class RegistrationResponse
    {
        // FAILED
        public string error { get; set; }
        public string error_description { get; set; }
        // SUCCESS
        public int error_code { get; set; }
    }
}
