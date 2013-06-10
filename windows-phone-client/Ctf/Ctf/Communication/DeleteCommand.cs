using Ctf.ApplicationTools;
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
    public class DeleteCommand : BaseCommand<DeleteResponse>
    {
        public DeleteCommand(String gameId)
            : base()
        {
            request = new RestRequest(String.Format("/api/secured/games/{0}", gameId), Method.DELETE);
            request.AddHeader("Accept", "application/json");
            request.AddHeader("Authorization", String.Format("{0} {1}", ApplicationSettings.Instance.RetriveLoggedUser().token_type, ApplicationSettings.Instance.RetriveLoggedUser().access_token));
        }

        protected override void RequestCallbackOnSuccess(IRestResponse<DeleteResponse> response)
        {
            if ((response != null) && (response.Data != null))
            {
                Debug.WriteLine(DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Response content: " + response.Content));
                Debug.WriteLineIf(response.Data.HasError(), DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Response is an Error."));
                Debug.WriteLineIf(!response.Data.HasError(), DebugInfo.Format(DateTime.Now, this, MethodInfo.GetCurrentMethod(), "Response is a Success."));
                OnRequestFinished(new RequestFinishedEventArgs(response.Data));
            }
        }

        public RestRequestAsyncHandle DeleteGame()
        {
            return ExecuteTrueAsync(request, RequestCallbackOnFinish);
        }
    }
}
