using RestSharp;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Ctf
{
    class RequestHandler
    {
        private RestClient client;

        public RequestHandler(String baseUrl)
        {
            client = new RestClient(baseUrl);
        }

        public async Task<RestRequestAsyncHandle> ExecuteAsync<T>(RestRequest request, Action<IRestResponse<T>> CallbackOnSuccess, Action<String> CallbackOnFail) where T : new()
        {
            RestRequestAsyncHandle asyncHandler = client.ExecuteAsync<T>(request, (response) =>
                {
                    if (response.ResponseStatus == ResponseStatus.Error)
                    {
                        CallbackOnFail(response.ErrorMessage);
                    }
                    else
                    {
                        CallbackOnSuccess(response);
                    }
                });

            return asyncHandler;
        }
    }
}
