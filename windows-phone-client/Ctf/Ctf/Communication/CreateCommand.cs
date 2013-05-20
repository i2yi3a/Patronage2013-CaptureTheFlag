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
    class CreateCommand : BaseCommand
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

      

        public Task<RestRequestAsyncHandle> CreateGame(Game game)
        {
            request.AddBody(new
            {
                name = game.name,
                description = game.description,
                time_start = game.time_start,
                duration = game.duration,
                points_max = game.points_max,
                players_max = game.players_max,
                localization = new
                {
                    name = game.localization.name,
                    latLng = new { lat = game.localization.latLng.lat, lng = game.localization.latLng.lng },
                    radius = game.localization.radius
                },
            });
            return requestHandler.ExecuteAsync<CreateJsonResponse>(request, RequestCallbackOnSuccess, RequestCallbackOnFail);
        }


        private void RequestCallbackOnSuccess(IRestResponse<CreateJsonResponse> response)
        {
            if ((response != null) && (response.Data != null))
            {
                Debug.WriteLine(DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Response content: " + response.Content));
                Debug.WriteLineIf(response.Data.HasError(), DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Response is an Error."));
                Debug.WriteLineIf(!response.Data.HasError(), DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Response is a Success."));
                OnRequestFinished(new RequestFinishedEventArgs(response.Data));
            }
        }

        private void RequestCallbackOnFail(String errorMessage)
        {
            Debug.WriteLine(DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Error message: " + errorMessage));
            OnRequestFinished(new RequestFinishedEventArgs(new ApplicationError(errorMessage)));
        }
    }
}

