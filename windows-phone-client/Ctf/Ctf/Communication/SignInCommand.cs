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
    class SignInCommand : BaseCommand<ServerResponse>
    {
        public SignInCommand(String gameId)
            : base()
        {
            request = new RestRequest(String.Format("/api/secured/games/{0}/signIn", gameId), Method.PUT);
            request.AddHeader("Accept", "application/json");
            request.AddHeader("Content-type", "application/json");
            request.AddHeader("Authorization", String.Format("{0} {1}", ApplicationSettings.Instance.RetriveLoggedUser().token_type, ApplicationSettings.Instance.RetriveLoggedUser().access_token));
            request.RequestFormat = DataFormat.Json;
        }

        public RestRequestAsyncHandle SignIn()
        {
            return ExcecuteAsyncCommand(request, RequestCallbackOnFinish);
        }
    }
}
