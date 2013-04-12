using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using RestSharp;
using System.Diagnostics;

namespace Ctf
{
    // BUG?: new { username = "a", password = "a" } registers and gets a token but is not visible in 'Player list' at Capture the flag :: API
    // User story: https://tracker.blstreamgroup.com/jira/browse/CTFPAT-55
    class Registration
    {
        private RequestHandler requestHandler;


        // NOTE: do not make requests in constructor even for testing purposes
        // NOTE: Above note doesn't seem to be correct in all cases ? (see login exceptions)
        public Registration()
        {
            requestHandler = new RequestHandler(CtfConstants.SERVER_BASE_URL);
        }

        private RestRequest createRequest()
        {
            RestRequest request = new RestRequest("/api/players/add", Method.POST);

            request.AddHeader("Accept", "application/json");
            request.AddHeader("Content-type", "application/json");

            request.RequestFormat = DataFormat.Json;
            request.AddBody(new { username = "a", password = "b" });

            return request;
        }

        private async void makeRequest(RestRequest request)
        {
            await requestHandler.ExecuteAsync<RegistrationResponse>(request, RequestCallbackOnSuccess, RequestCallbackOnFail);
        }

        //* Nazwa użytkownika musi być unikalna.
        private void RequestCallbackOnSuccess(IRestResponse<RegistrationResponse> response)
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

        private void RequestCallbackOnFail(String errorMessage)
        {
            Debug.WriteLine(errorMessage);
        }

        //* Hasło musi mieć minimum 5 znaków,
        //* Nazwa użytkownika musi mieć minimum 5 znaków,
        // TODO: Create UserCredentials class?


        //* Można się zarejestrować,
        // Issue: https://tracker.blstreamgroup.com/jira/browse/CTFPAT-88
        public bool register(string username, string firstPassword, string secondPassword)
        {

            RestRequest r = createRequest();
            makeRequest(r);

            return false;
        }
    }
}
