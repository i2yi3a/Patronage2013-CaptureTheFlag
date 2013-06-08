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
using Ctf.Models.DataObjects;

namespace Ctf.Communication
{
    class GameInfoCommand : BaseCommand<GameDetails>
    {
        public GameInfoCommand(String gameId)
            : base()
        {
            request = new RestRequest(String.Format("/api/secured/games/{0}", gameId), Method.GET);
            request.AddHeader("Accept", "application/json; charset=utf-8");
            //request.AddHeader("Content-type", "application/json");
            request.AddHeader("Authorization", String.Format("{0} {1}", ApplicationSettings.Instance.RetriveLoggedUser().token_type, ApplicationSettings.Instance.RetriveLoggedUser().access_token));
            //request.RequestFormat = DataFormat.Json;
        }

        protected override void RequestCallbackOnSuccess(IRestResponse<GameDetails> response)
        {
            if ((response != null) && (response.Data != null))
            {
                Debug.WriteLine(DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Response content: " + response.Content));
                Debug.WriteLineIf(response.Data.HasError(), DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Response is an Error."));
                Debug.WriteLineIf(!response.Data.HasError(), DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Response is a Success."));
                OnRequestFinished(new RequestFinishedEventArgs(response.Data));
            }
        }

        //private void RequestCallbackOnFail(String errorMessage)
        //{
        //    Debug.WriteLine(DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Error message: " + errorMessage));
        //    OnRequestFinished(new RequestFinishedEventArgs(new ApplicationError(errorMessage)));
        //}

        public RestRequestAsyncHandle GetGameInfo()
        {
            return ExecuteAsync(request, RequestCallbackOnSuccess, RequestCallbackOnFail);
        }
    }
}
