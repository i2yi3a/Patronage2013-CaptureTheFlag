using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RestSharp;
using System.Reflection;
using System.Diagnostics;
using Ctf.ApplicationTools;
using Ctf.ApplicationTools.DataObjects;
using Ctf.Communication.DataObjects;

namespace Ctf.Communication
{
    class EditGameCommand : BaseCommand<ServerResponse>
    {
        public EditGameCommand(String gameId) : base()
        {
            request = new RestRequest(String.Format("/api/secured/games/{0}", gameId), Method.PUT);
            request.AddHeader("Accept", "application/json");
            request.AddHeader("Content-type", "application/json");
            request.AddHeader("Authorization", String.Format("{0} {1}", ApplicationSettings.Instance.RetriveLoggedUser().token_type, ApplicationSettings.Instance.RetriveLoggedUser().access_token));
            request.RequestFormat = DataFormat.Json;
        }

        public RestRequestAsyncHandle EditGame(Game GameInfo)
        {
            request.AddBody(GameInfo);
            return ExcecuteAsyncCommand(request, RequestCallbackOnFinish);
        }
    }
}
