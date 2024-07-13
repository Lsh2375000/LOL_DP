

async function getPuuidByTagLine(summonerName, tagName) {
    const gameName = summonerName;
    const tagLine = tagName;

    const request = await axios.get(`https://asia.api.riotgames.com/riot/account/v1/accounts/by-riot-id/${gameName}/${tagLine}?api_key=${apiKey}`);
    return request.data;
}


async function getIdByPuuid(puuid) {
    const request = await axios.get(`https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-puuid/${puuid}?api_key=${apiKey}`);
    return request.data;
}

async function getRankInfoById(summonerId) {
    const request = await axios.get(`https://kr.api.riotgames.com/lol/league/v4/entries/by-summoner/${summonerId}?api_key=${apiKey}`);
    return request.data;
}

async function getRiotApi(summonerName) {
    const request = await axios.get(`https://kr.api.riotgames.com/lol/summoner/v4/summoners/by-name/${summonerName}?api_key=${apiKey}`);
    return request.data;
}

const apiKey = "RGAPI-36bbc6b1-83fd-4b39-8c76-01ecaf4c41a6";

