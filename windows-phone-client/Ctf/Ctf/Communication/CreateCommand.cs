using Ctf.ApplicationTools;
using Ctf.ApplicationTools.DataObjects;
using Ctf.Communication.DataObjects;
using RestSharp;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace Ctf.Communication
{
    class CreateCommand : BaseCommand<Game>
    {
        public CreateCommand()
            :base()
        {
            request = new RestRequest("/api/secured/games", Method.POST);
            request.AddHeader("Accept", "application/json");
            request.AddHeader("Content-type", "application/json");
            request.AddHeader("Authorization", "Bearer " + ApplicationSettings.Instance.RetriveLoggedUser().access_token.ToString());
            request.RequestFormat = DataFormat.Json;
        }

        public RestRequestAsyncHandle CreateGame(Game game)
        {
            request.AddBody(game);
            return ExecuteAsync(request, RequestCallbackOnSuccess, RequestCallbackOnFail);
        }


        protected override void RequestCallbackOnSuccess(IRestResponse<Game> response)
        {
            if ((response != null) && (response.Data != null))
            {
                Debug.WriteLine(DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Response content: " + response.Content));
                OnRequestFinished(new RequestFinishedEventArgs(response.Data));
            }
        }
    }
}

