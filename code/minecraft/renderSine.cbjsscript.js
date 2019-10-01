import("util.command");import("math");import("chat.tellraw");
fixed start = math.pi();start *= -1; //start at -pi
fixed stop = math.pi(); //stop at pi
for(fixed i = start; i <= stop; i += 0.2){    fixed result = math.sin(i);    tellraw("Sine of ", i, " is ", result);    fixed x = i;    renderValue(x, result);}
function renderValue(fixed x, fixed y){    command("summon ArmorStand 0 10 0 {CustomName:curr,NoGravity:true,Invisible:true,Equipment:[{},{},{},{},{id:wool,Count:1b}]}");    if(x >= 0)    {        while(x >= 1)        {            command("tp @e[name=curr] ~1 ~ ~");            x -= 1;        }        while(x >= 0.1)        {            command("tp @e[name=curr] ~0.1 ~ ~");            x -= 0.1;        }    }    else    {        while(x <= -1)        {            command("tp @e[name=curr] ~-1 ~ ~");            x += 1;        }        while(x <= -0.1)        {            command("tp @e[name=curr] ~-0.1 ~ ~");            x += 0.1;        }    }
    if(y >= 0)    {        while(y >= 0.1)        {            command("tp @e[name=curr] ~ ~0.1 ~");            y -= 0.1;        }    }    else    {        while(y <= -0.1)        {            command("tp @e[name=curr] ~ ~-0.1 ~");            y += 0.1;        }    }
    command("entitydata @e[name=curr] {CustomName:done}");}
