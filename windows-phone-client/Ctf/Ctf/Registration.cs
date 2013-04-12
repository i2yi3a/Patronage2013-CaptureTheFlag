using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RestSharp;
using System.Diagnostics;

namespace Ctf
{
    class Registration
    {
        private RequestHandler requestHandler;

        // NOTE: do not make requests in constructor even for testing purposes
        public Registration()
        {
            requestHandler = new RequestHandler(CtfConstants.SERVER_BASE_URL);
        }

        public RestRequest createRequest()
        {
            RestRequest request = new RestRequest("/api/players/add", Method.POST);

            request.AddHeader("Accept", "application/json");
            request.AddHeader("Content-type", "application/json");

            request.RequestFormat = DataFormat.Json;
            request.AddBody(new { username = "michal.krawczak@blstream.com", password = "ExamPle3retPassw0rd!" });

            return request;
        }

        public async void makeRequest(RestRequest request)
        {
            await requestHandler.ExecuteAsync<RegistrationResponse>(request, RequestCallbackOnSuccess, RequestCallbackOnFail);
        }

        public void RequestCallbackOnSuccess(IRestResponse<RegistrationResponse> response)
        {
            if (response != null)
            {
                if (response.Data != null)
                {
                    Debug.WriteLine("Content: " + response.Content);
                    Debug.WriteLine("response.Data.error_code: " + response.Data.error_code);

                    Debug.WriteLine("response.Data.error: " + response.Data.error);
                    Debug.WriteLine("response.Data.error_description: " + response.Data.error_description);
                }
            }
        }

        public void RequestCallbackOnFail(String errorMessage)
        {
            Debug.WriteLine(errorMessage);
        }
    }
}
