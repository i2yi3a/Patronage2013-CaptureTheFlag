using Ctf.ApplicationTools;
using Ctf.ApplicationTools.DataObjects;
using Ctf.Communication;
using Ctf.Communication.DataObjects;
using Ctf.Models;
using RestSharp;
using System;
using System.Collections.Generic;
using System.Diagnostics;
using System.Linq;
using System.Reflection;
using System.Text;
using System.Threading.Tasks;

namespace Ctf.ViewModels
{
    public class FetchGamesCommand : BaseCommand
    {
        public FetchGamesCommand()
            : base()
        {
            request = new RestRequest("/api/secured/games/", Method.GET);
            request.AddHeader("Accept", "application/json");
            request.AddHeader("Authorization", String.Format("{0} {1}", ApplicationSettings.Instance.RetriveLoggedUser().token_type, ApplicationSettings.Instance.RetriveLoggedUser().access_token));
            request.OnBeforeDeserialization = response => { response.Content = "{ \"games\" : " + response.Content + "}"; };
        }

        private void RequestCallbackOnSuccess(IRestResponse<JsonGames> response)
        {
            if ((response != null) && (response.Data != null))
            {
                Debug.WriteLine(DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Response content: " + response.Content));
                Debug.WriteLineIf(response.Data.HasError(), DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Response is an Error."));
                Debug.WriteLineIf(!response.Data.HasError(), DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Response is a Success."));
                OnRequestFinished(new RequestFinishedEventArgs(response.Data));
                foreach (GameHeader g in response.Data.games)
                {
                    Debug.WriteLine("id:"+g.Id+", name:"+g.Name+", owner:"+g.Owner+", status:"+g.Status);
                }
            }
        }

        private void RequestCallbackOnFail(String errorMessage)
        {
            Debug.WriteLine(DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Error message: " + errorMessage));
            OnRequestFinished(new RequestFinishedEventArgs(new ApplicationError(errorMessage)));
        }

        public async Task<RestRequestAsyncHandle> FetchGames()
        {
            return await requestHandler.ExecuteAsync<JsonGames>(request, RequestCallbackOnSuccess, RequestCallbackOnFail);
        }
    }
}
