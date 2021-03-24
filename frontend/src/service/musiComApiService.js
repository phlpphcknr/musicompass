import axios from 'axios'

const baseUrl = '/api'

export const getArtistSearchResult = (artistSearchTerm) =>
  axios
    .get(baseUrl + `/artistsearch/${artistSearchTerm}`)
    .then((response) => response.data)

export const getArtistByName = (artistName) =>
    axios
        .get(baseUrl + `/artist/${artistName}`)
        .then((response) => response.data)
