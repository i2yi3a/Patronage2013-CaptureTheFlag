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
    class EditGameCommand : BaseCommand
    {
        public EditGameCommand(String gameId) : base()
        {
            request = new RestRequest(String.Format("/api/secured/games/{0}", gameId), Method.PUT);
            request.AddHeader("Accept", "application/json");
            request.AddHeader("Content-type", "application/json");
            request.AddHeader("Authorization", String.Format("Bearer {0}", ApplicationSettings.Instance.RetriveLoggedUser().access_token));
            request.RequestFormat = DataFormat.Json;
        }

        public void RequestCallbackOnSuccess(IRestResponse<ServerJsonResponse> response)
        {
            if ((response != null) && (response.Data != null))
            {
                if ((response != null) && (response.Data != null))
                {
                    Debug.WriteLine(DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Response content: " + response.Content));
                    Debug.WriteLineIf(response.Data.HasError(), DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Response is an Error."));
                    Debug.WriteLineIf(!response.Data.HasError(), DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Response is a Success."));
                    OnRequestFinished(new RequestFinishedEventArgs(response.Data));
                }
            }
        }

        public void RequestCallbackOnFail(String errorMessage)
        {
            Debug.WriteLine(DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Error message: " + errorMessage));
            OnRequestFinished(new RequestFinishedEventArgs(new ApplicationError(errorMessage)));
        }

        public async Task<RestRequestAsyncHandle> Register(UserCredentials user)
        {
            request.AddBody(new { username = user.username, password = user.password });
            return await requestHandler.ExecuteAsync<ServerJsonResponse>(request, RequestCallbackOnSuccess, RequestCallbackOnFail);
        }
    }
}
