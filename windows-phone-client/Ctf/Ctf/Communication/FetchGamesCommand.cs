using Ctf.ApplicationTools;
using Ctf.ApplicationTools.DataObjects;
using Ctf.Communication;
using Ctf.Communication.DataObjects;
using Ctf.Models;
using Ctf.Models.DataObjects;
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
    public class FetchGamesCommand<T> : BaseCommand<T> where T : ResponseBase, new()
    {
        public FetchGamesCommand()
            : base()
        {
            request = new RestRequest("/api/secured/games/", Method.GET);
            request.AddHeader("Accept", "application/json");
            request.AddHeader("Authorization", String.Format("{0} {1}", ApplicationSettings.Instance.RetriveLoggedUser().token_type, ApplicationSettings.Instance.RetriveLoggedUser().access_token));
            request.OnBeforeDeserialization = response => { response.Content = "{ \"games\" : " + response.Content + "}"; };
        }

        public FetchGamesCommand(GameIdentificator gameIdentificator)
            : base()
        {
            request = new RestRequest(String.Format("/api/secured/games/{0}",gameIdentificator.id), Method.GET);
            request.AddHeader("Accept", "application/json");
            request.AddHeader("Authorization", String.Format("{0} {1}", ApplicationSettings.Instance.RetriveLoggedUser().token_type, ApplicationSettings.Instance.RetriveLoggedUser().access_token));
        }

        public FetchGamesCommand(LocationFilter locationFilter)
            : base()
        {
            request = new RestRequest(String.Format("/api/secured/games/nearest?latLng={0},{1}&range={2}&status={3}", locationFilter.latLng[0], locationFilter.latLng[1], locationFilter.range, locationFilter.status), Method.GET);
            request.AddHeader("Accept", "application/json");
            request.AddHeader("Authorization", String.Format("{0} {1}", ApplicationSettings.Instance.RetriveLoggedUser().token_type, ApplicationSettings.Instance.RetriveLoggedUser().access_token));
            request.OnBeforeDeserialization = response => { response.Content = "{ \"games\" : " + response.Content + "}"; };
        }

        public FetchGamesCommand(GameHeader gameHeader)
            : base()
        {
            request = new RestRequest(String.Format("/api/secured/games?name={0}&status={1}&myGamesOnly={2}", gameHeader.Name, gameHeader.Status, gameHeader.Owner.Equals(ApplicationSettings.Instance.RetriveLoggedUser().username)), Method.GET);
            request.AddHeader("Accept", "application/json");
            request.AddHeader("Authorization", String.Format("{0} {1}", ApplicationSettings.Instance.RetriveLoggedUser().token_type, ApplicationSettings.Instance.RetriveLoggedUser().access_token));
            request.OnBeforeDeserialization = response => { response.Content = "{ \"games\" : " + response.Content + "}"; };
        }

        public RestRequestAsyncHandle ExcecuteAsyncCommand()
        {
            System.Diagnostics.Debug.WriteLine("public RestRequestAsyncHandle ExcecuteAsyncCommand()");
            return ExcecuteAsyncCommand(request, RequestCallbackOnFinish);
        }
    }
}
