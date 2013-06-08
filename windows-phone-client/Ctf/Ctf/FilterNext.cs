using Ctf.ApplicationTools;
using Ctf.Models.DataObjects;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;

namespace PhoneApp3
{
    public class FilterNext
    {
        public FilterNext()
        {

        }

        public ObservableCollection<GameHeader> Execute(ObservableCollection<GameHeader> source, string filter)
        {
            ObservableCollection<GameHeader> filtered = new ObservableCollection<GameHeader>();
            Regex match = new Regex(filter, RegexOptions.IgnoreCase);
            foreach (GameHeader input in source)
            {
                if (match.IsMatch(input.Name))
                    filtered.Add(input);
            }
            return filtered;
        }

        public Task<ObservableCollection<GameHeader>> ByNameAsync(ObservableCollection<GameHeader> source, string filter)
        {
            ObservableCollection<GameHeader> filtered = new ObservableCollection<GameHeader>();
            Regex match = new Regex(filter, RegexOptions.IgnoreCase);
            Task<ObservableCollection<GameHeader>> taskHandle = Task<ObservableCollection<GameHeader>>.Run(() =>
            {
                foreach (GameHeader input in source)
                {
                    if (input.Name != null)
                    {
                        if (match.IsMatch(input.Name))
                            filtered.Add(input);
                    }
                }
                return filtered;
            });
            return taskHandle;
        }

        public Task<ObservableCollection<GameHeader>> ByOwnerAsync(ObservableCollection<GameHeader> source, string filter)
        {
            ObservableCollection<GameHeader> filtered = new ObservableCollection<GameHeader>();
            Regex match = new Regex(filter, RegexOptions.IgnoreCase);
            Task<ObservableCollection<GameHeader>> taskHandle = Task<ObservableCollection<GameHeader>>.Run(() =>
            {
                foreach (GameHeader input in source)
                {
                    if (input.Owner != null)
                    {
                        if (match.IsMatch(input.Owner))
                            filtered.Add(input);
                    }
                }
                return filtered;
            });
            return taskHandle;
        }

        public Task<ObservableCollection<GameHeader>> ByStatusAsync(ObservableCollection<GameHeader> source, string filter)
        {
            ObservableCollection<GameHeader> filtered = new ObservableCollection<GameHeader>();
            Regex match = new Regex(filter, RegexOptions.IgnoreCase);
            Task<ObservableCollection<GameHeader>> taskHandle = Task<ObservableCollection<GameHeader>>.Run(() =>
            {
                foreach (GameHeader input in source)
                {
                    if (input.Status != null)
                    {
                        if (match.IsMatch(input.Status))
                            filtered.Add(input);
                    }
                }
                return filtered;
            });
            return taskHandle;
        }

        public Task<ObservableCollection<GameHeader>> ByDistanceAsync(ObservableCollection<GameHeader> source, List<double> filter, double range)
        {
            ObservableCollection<GameHeader> filtered = new ObservableCollection<GameHeader>();
            Task<ObservableCollection<GameHeader>> taskHandle = Task<ObservableCollection<GameHeader>>.Run(() =>
            {
                foreach (GameHeader input in source)
                {
                    if (input.localization != null && input.localization.latLng != null)
                    {
                        if (Geo.Distance(filter, input.localization.latLng) <= range)
                            filtered.Add(input);
                    }
                }
                return filtered;
            });
            return taskHandle;
        }

        public Task<ObservableCollection<GameHeader>> ByLocationAsync(ObservableCollection<GameHeader> source, string filter)
        {
            ObservableCollection<GameHeader> filtered = new ObservableCollection<GameHeader>();
            Regex match = new Regex(filter, RegexOptions.IgnoreCase);
            Task<ObservableCollection<GameHeader>> taskHandle = Task<ObservableCollection<GameHeader>>.Run(() =>
            {
                foreach (GameHeader input in source)
                {
                    if (input.Name != null)
                    {
                        if (match.IsMatch(input.Name))
                            filtered.Add(input);
                    }
                }
                return filtered;
            });
            return taskHandle;
        }

        public Task<ObservableCollection<GameHeader>> BySlotsAsync(ObservableCollection<GameHeader> source, int slots)
        {
            ObservableCollection<GameHeader> filtered = new ObservableCollection<GameHeader>();
            Task<ObservableCollection<GameHeader>> taskHandle = Task<ObservableCollection<GameHeader>>.Run(() =>
            {
                foreach (GameHeader input in source)
                {
                    if (input.PlayersCount != null && input.PlayersMax != null)
                    {
                        if (input.PlayersCount + slots <= input.PlayersMax)
                            filtered.Add(input);
                    }
                }
                return filtered;
            });
            return taskHandle;
        }
    }
}
