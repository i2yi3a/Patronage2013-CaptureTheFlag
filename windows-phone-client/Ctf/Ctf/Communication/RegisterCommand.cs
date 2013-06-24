using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RestSharp;
using System.Diagnostics;
using System.Reflection;
using Ctf.Communication.DataObjects;
using Ctf.ApplicationTools.DataObjects;
using Ctf.ApplicationTools;

namespace Ctf.Communication
{
    class RegisterCommand : BaseCommand<ServerResponse>
    {
        /// <summary>
        /// Initializes a new instance of the <see cref="Registration"/> class.
        /// </summary>
        public RegisterCommand()
            : base()
        {
            request = new RestRequest("/api/players/add", Method.POST);
            request.AddHeader("Accept", "application/json");
            request.AddHeader("Content-type", "application/json");
            request.RequestFormat = DataFormat.Json;
        }

        public RestRequestAsyncHandle ExecuteCommand(UserCredentials user)
        {
            request.AddBody(new { username = user.username, password = user.password });
            return ExcecuteAsyncCommand(request, RequestCallbackOnFinish);
        }
    }
}
