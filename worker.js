export default {
  async fetch(request) {
    return new Response("Działa, gratulacje!", {
      headers: { "Content-Type": "text/plain" }
    });
  }
};
