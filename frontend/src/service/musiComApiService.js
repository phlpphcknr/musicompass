import axios from 'axios'

const baseUrl = '/api'

export const getArtistSearchResult = (artistSearchTerm) =>
    axios.get(baseUrl + `/artist/${artistSearchTerm}`).then((response) => response.data)