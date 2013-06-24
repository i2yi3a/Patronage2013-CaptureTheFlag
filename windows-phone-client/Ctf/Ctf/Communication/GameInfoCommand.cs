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
            request.AddHeader("Authorization", String.Format("{0} {1}", ApplicationSettings.Instance.RetriveLoggedUser().token_type, ApplicationSettings.Instance.RetriveLoggedUser().access_token));
        }

        public RestRequestAsyncHandle GetGameInfo()
        {
            return ExcecuteAsyncCommand(request, RequestCallbackOnFinish);
        }
    }
}
