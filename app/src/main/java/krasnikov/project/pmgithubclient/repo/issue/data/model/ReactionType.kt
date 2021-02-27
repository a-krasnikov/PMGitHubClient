package krasnikov.project.pmgithubclient.repo.issue.data.model

enum class ReactionType(val content: String, val emoji: Int) {
    PlusOne("+1",0x1F44D),
    MinusOne("-1",0x1F44E),
    Laugh("laugh",0x1F604),
    Confused("confused",0x1F615),
    Heart("heart",0x2764),
    Hooray("hooray", 0x1F389),
    Rocket("rocket",0x1F680),
    Eyes("eyes",0x1F440)
}