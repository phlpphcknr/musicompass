import axios from 'axios'

const baseUrl = '/api'

export const getArtistSearchResult = (artistSearchTerm) =>
  axios
    .get(baseUrl + `/artistsearch/${artistSearchTerm}`)
    .then((response) => response.data)
